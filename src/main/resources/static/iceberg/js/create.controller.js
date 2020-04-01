//controler
(function () {
    'use strict';

    angular.module('app').controller('appControllerCreate', appControllerCreate);
    appControllerCreate.$inject = ['appService', '$scope', '$localstorage', '$location', '$mdDialog', '$mdToast'];

    function appControllerCreate(appService, $scope, $localstorage, $location, $mdDialog, $mdToast) {

        $scope.readURL = readURL;
        $scope.ajouterCostume = ajouterCostume;
        $scope.goToHome = goToHome;
        $scope.setImage = setImage;
        $scope.showDialogNewBox = showDialogNewBox;
        $scope.addNewBox = addNewBox;
        $scope.closedModalNewBox = closedModalNewBox;

        $scope.listCouleurs = [];
        $scope.listTypes = [];
        $scope.listEmplacements = [];
        $scope.listEmplacements = [];
        $scope.gridOptionsTailles = {};
        $scope.listImg = [];
        $scope.fileUpload = null;
        $scope.objet = {
            dateDepot: null,
            id: null,
            image1: null,
            image1ContentType: null,
            image2: null,
            image2ContentType: null,
            image3: null,
            image3ContentType: null,
            image4: null,
            image4ContentType: null,
            libelle: null,
            userId: null
        };
        $scope.costumeLabel;
        $scope.valeurType;
        $scope.valeurCouleur;
        $scope.valeurEmplacement;
        $scope.searchTerm;
        $scope.clearSearchTerm = function () {
            $scope.searchTerm = '';
        };
        $scope.canAddNewBox = true;
        $scope.displayModal = false;

        $scope.toModify = false;
        $scope.objectModify = {};

        var vm = this;
        init();

        function init() {
            initLists();
            if ($scope.gridOptionsTailles.data === undefined) {
                initGridOptions();
            }

            initObject();

            var myIFRame = window.parent.$("iframe#appIceberg");
            myIFRame.scrollTop();
            var largeur_fenetre = $(window.parent).width();
            var hauteur = 600;
            if (largeur_fenetre < 750)
                hauteur = 1024;
            else if (largeur_fenetre < 1170)
                hauteur = 700;
            myIFRame.attr('height', hauteur + 'px');
            window.parent.$("body").scrollTop(0);
        }

        function initLists() {
            $scope.listCouleurs = $localstorage.getCouleurs();
            if ($scope.listCouleurs == null || $scope.listCouleurs == undefined) {
                appService.getAllCouleurs().success(function (data) {
                    $localstorage.saveCouleurs(data);
                    $scope.listCouleurs = data;
                 //   initObject();
                });
            }

            $scope.listTypes = $localstorage.getTypes();
            if ($scope.listTypes == null || $scope.listTypes == undefined) {
                appService.getAllTypes().success(function (data) {
                    $localstorage.saveTypes(data);
                    $scope.listTypes = data;
            //        initObject();
                });
            }

            $scope.listEmplacements = $localstorage.getEmplacements();
            if ($scope.listEmplacements == null || $scope.listEmplacements == undefined) {
                appService.getAllEmplacement().success(function (data) {
                    $localstorage.saveEmplacements(data);
                    $scope.listEmplacements = data;
               //     initObject();
                });
            }
        }

        function initObject() {
            $scope.objectModify = $localstorage.getObjectModify();
            if ($scope.objectModify) {
                $scope.objet.id = $scope.objectModify.id;
                $scope.toModify = true;
                $scope.costumeLabel = $scope.objectModify.libelle;
                if ($scope.listTypes) {
                    $scope.valeurType = $scope.listTypes.filter(function (t) {
                        return t.type === $scope.objectModify.type.valeur;
                    })[0];
                }
                if ($scope.listCouleurs) {
                    $scope.valeurCouleur = $scope.listCouleurs.filter(function (t) {
                        return t.couleur === $scope.objectModify.couleur.valeur;
                    })[0];
                }
                if ($scope.listEmplacements) {
                    $scope.valeurEmplacement = $scope.listEmplacements.filter(function (t) {
                        return t.id === $scope.objectModify.emplacement.paramEmpl.id;
                    })[0];
                }
                var tailles = [];

                //toutes les tailles dispos
                var i = 0;
                var getAllTailles = $localstorage.getTailles();
                //version taille sans cache
                if (getAllTailles == null || getAllTailles == undefined) {
                	console.log("recherche des infos taille");
                    appService.getAllTailles().success(function (data) {
                        $localstorage.saveTailles(data);
                        data.forEach(function (uneTaille) {
                            tailles.push({
                                idTaille: uneTaille.id,
                                taille: uneTaille.taille,
                                qte: null
                            });
                        
                            //est-ce qu'il y une quantité sur cette taille ?
                            $scope.objectModify.detailEmplacement
                            	.forEach(function (detail) {
                            		if(detail.caracteristique == "TAILLE" && detail.valeurCaracteristique == uneTaille.taille){
                            			tailles[i].qte = detail.qteEnCours;
                            		}
                            	});
                            
                            i = i+1;
                            
                        }); });
                }else { //version taille en cache
                    getAllTailles.forEach(function (uneTaille) {
                        tailles.push({
                            idTaille: uneTaille.id,
                            taille: uneTaille.taille,
                            qte: null
                        })
                        
                        //est-ce qu'il y une quantité sur cette taille ?
                        $scope.objectModify.detailEmplacement
                        	.forEach(function (detail) {
                        		if(detail.caracteristique == "TAILLE" && detail.valeurCaracteristique == uneTaille.taille){
                        			tailles[i].qte = detail.qteEnCours;
                        		}
                        	});
                        
                        i = i+1;
                    }); 
                }
                    
                $scope.gridOptionsTailles.data = tailles;
                $scope.objet.image1 = $scope.objectModify.image1;
                $scope.objet.image1ContentType = $scope.objectModify.image1ContentType;
                $scope.objet.image2 = $scope.objectModify.image2;
                $scope.objet.image2ContentType = $scope.objectModify.image2ContentType;
            }
        }

        function initGridOptions() {
            $scope.gridOptionsTailles.rowHeight = 30;
            $scope.gridOptionsTailles.enableHorizontalScrollbar = 0;
            $scope.gridOptionsTailles.enableColumnMenus = false;
            $scope.gridOptionsTailles.columnDefs = [
                {
                    field: 'taille',
                    enableFiltering: true,
                    pinnedLeft: true,
                    enableCellEdit: false,
                    width: 115,
                    headerCellClass: "center",
                    cellClass: 'center cellNoEditable'
                },
                {
                    field: 'qte',
                    enableFiltering: true,
                    enableCellEdit: true,
                    type: 'number',
                    width: 115,
                    headerCellClass: "center",
                    cellClass: 'center'
                }
            ];

            $scope.gridOptionsTailles.onRegisterApi = function (gridApi) {
                $scope.gridApi = gridApi;
            };

            if (! $scope.toModify) {
            var tailles = [];
            var tmp = $localstorage.getTailles();
	            if (tmp == null || tmp == undefined) {
	                appService.getAllTailles().success(function (data) {
	                    $localstorage.saveTailles(data);
	                    tmp = data;
	                    tmp.forEach(function (t) {
	                        tailles.push({
	                            idTaille: t.id,
	                            taille: t.taille,
	                            qte: null
	                        });
	                       $scope.gridOptionsTailles.data = tailles;
	                    });
	                });
	            }
            }
            $scope.gridOptionsTailles.data = initData();
        }

        function initData() {
            var tail = [];
            var tmp = $localstorage.getTailles();
            if (tmp == null || tmp == undefined) {
                appService.getAllTailles().success(function (data) {
                    $localstorage.saveTailles(data);
                    tmp = data;
                    tmp.forEach(function (t) {
                        if($scope.toModify){
                            $scope.objectModify.detailEmplacement.forEach(function(detail){
                               if(detail.caracteristique == "TAILLE" && detail.valeurCaracteristique == t.taille){
                                   tail[0].qte = detail.qteEnCours;
                               } else {
                                   tail.push({
                                       idTaille: t.id,
                                       taille: t.taille,
                                       qte: null
                                   });
                               }
                            });
                        } else {
                            tail.push({
                                idTaille: t.id,
                                taille: t.taille,
                                qte: null
                            });
                        }
                    });
                    return tail;
                });
            } else {
                tmp.forEach(function (t) {
                    if($scope.toModify){
                        $scope.objectModify.detailEmplacement.forEach(function(detail){
                            if(detail.caracteristique == "TAILLE" && detail.valeurCaracteristique == t.taille){
                                tail.push({
                                    idTaille: t.id,
                                    taille: t.taille,
                                    qte: detail.qteEnCours
                                });
                            } else {
                                tail.push({
                                    idTaille: t.id,
                                    taille: t.taille,
                                    qte: null
                                });
                            }
                        });
                    } else {
                        tail.push({
                            idTaille: t.id,
                            taille: t.taille,
                            qte: null
                        });
                    }
                });
                return tail;
            }
        }

        function transformImage(image) {
            var canvasDataURL;

            var maxWidth = 450;
            var maxHeight = 600;

            var orientation;
            window.EXIF.getData(image, function () {
                orientation = EXIF.getTag(this, "Orientation");
            });

            var width = image.width;
            var height = image.height;

            var newWidth;
            var newHeight;

            if (width > height) {
                newHeight = height * (maxWidth / width);
                newWidth = maxWidth;
            } else {
                newWidth = width * (maxHeight / height);
                newHeight = maxHeight;
            }

            var canvas = document.createElement('canvas');
            // set proper canvas dimensions before transform & export
            if (4 < orientation && orientation < 9) {
                canvas.width = newHeight;
                canvas.height = newWidth;
            } else {
                canvas.width = newWidth;
                canvas.height = newHeight;
            }

            var ctx = canvas.getContext("2d");
            // transform context before drawing image
            switch (orientation) {
                case 2:
                    ctx.transform(-1, 0, 0, 1, newWidth, 0);
                    break;
                case 3:
                    ctx.transform(-1, 0, 0, -1, newWidth, newHeight);
                    break;
                case 4:
                    ctx.transform(1, 0, 0, -1, 0, newHeight);
                    break;
                case 5:
                    ctx.transform(0, 1, 1, 0, 0, 0);
                    break;
                case 6:
                    ctx.transform(0, 1, -1, 0, newHeight, 0);
                    break;
                case 7:
                    ctx.transform(0, -1, -1, 0, newHeight, newWidth);
                    break;
                case 8:
                    ctx.transform(0, -1, 1, 0, 0, newWidth);
                    break;
                default:
                    break;
            }
            ctx.drawImage(image, 0, 0, newWidth, newHeight);

            canvasDataURL = canvas.toDataURL("image/jpeg");
            return canvasDataURL;
        }

        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                if (idxImageClick == 1) {
                    reader.onload = function (e) {
                        var image = new Image();
                        image.src = e.target.result;
                        var canvasDataURL;
                        image.onload = function () {
                            canvasDataURL = transformImage(image);
                            $('#img').attr('src', canvasDataURL);
                            $scope.objet.image1 = canvasDataURL.substr(canvasDataURL.indexOf('base64,') + 'base64,'.length);

                        }
                    };
                    reader.readAsDataURL(input.files[0]);
                    if (input.files[0].type.substr(0, 6) == "image/") {
                        if ($scope.listImg[0] == null)
                            $scope.listImg.push(input.files[0]);
                        else
                            $scope.listImg[0] = input.files[0];
                        $scope.objet.image1ContentType = input.files[0].type;
                        document.getElementById("imgInp").value = "";
                    } else {
                        console.log("Seule les images peuvent être ajouté");
                        showWarningToast("Seule les images peuvent être ajouté");
                    }
                } else if (idxImageClick == 2) {
                    reader.onload = function (e) {
                        var image = new Image();
                        image.src = e.target.result;

                        var canvasDataURL;
                        image.onload = function () {
                            canvasDataURL = transformImage(image);
                            $('#img2').attr('src', canvasDataURL);
                            $scope.objet.image2 = canvasDataURL.substr(canvasDataURL.indexOf('base64,') + 'base64,'.length);

                        }
                    };
                    reader.readAsDataURL(input.files[0]);
                    if (input.files[0].type.substr(0, 6) == "image/") {
                        $scope.listImg.push(input.files[0]);
                        $scope.objet.image2ContentType = input.files[0].type;
                        document.getElementById("imgInp").value = "";
                    } else {
                        showWarningToast("Seule les images peuvent être ajouté");
                    }
                } else {
                    showWarningToast("Tu ne peux ajouter que 2 images");
                }
            }
        }

        $("#imgInp").change(function () {
            readURL(this);
        });

        var idxImageClick = 1;

        function setImage(idx) {
            idxImageClick = idx;
            document.getElementById('imgInp').click();
        }

        function ajouterCostume() {
            $scope.objet.dateDepot = new Date();
            $scope.objet.libelle = $scope.costumeLabel;
            $scope.objet.userId = 2;
            if (addIsPossible()) {
                    appService.updateObject($scope.objet)
                        .success(function (data) {
                            insertType(data.id);
                            insertCouleur(data.id);
                            insertEmplacement(data.id);
                            showInfoToast("Ton produit a bien été mis à jour");
                            $localstorage.removeObjectModify();
                            goToHome();
                        })
                        .catch(function (error) {
                            $localstorage.removeCanAddNewBox();
                            console.log(error);
                            showErrorToast("Une erreur est survenue lors de la mise à jour du costumre");
                        });

            } else {
                showWarningToast("Merci de bien renseigner chaque élément pour ajouter un produit");
            }
        }

        function addIsPossible() {
            var list = $scope.gridApi.core.getVisibleRows();
            $scope.qteTotal = 0;
            list.forEach(function (data) {
                if (data.entity.qte != null) {
                    $scope.qteTotal += data.entity.qte;
                }
            });
            var costumeLabelIsPresent = $scope.costumeLabel != undefined && $scope.costumeLabel != "" && $scope.costumeLabel != null;
            return (!costumeLabelIsPresent || $scope.valeurType == undefined || $scope.valeurCouleur == undefined || $scope.qteTotal == 0) ? false : true
        }

        function insertType(idObjet) {
            var objetCaracteristique = {
                id: null,
                objetId: idObjet,
                caracteristique: "TYPE",
                valeur: $scope.valeurType.type
            };
            if($scope.toModify){
                objetCaracteristique.id = $scope.objectModify.type.id;
            }
            appService.updateCaracteristique(objetCaracteristique)
                .success(function (data) {
                })
                .catch(function (error) {
                    showErrorToast("Une erreur est survenue lors de la création")
                });
        }

        function insertCouleur(idObjet) {
            var objetCaracteristique = {
                id: null,
                objetId: idObjet,
                caracteristique: "COULEUR",
                valeur: $scope.valeurCouleur.couleur
            };

            if($scope.toModify){
                objetCaracteristique.id = $scope.objectModify.couleur.id;
            }

            appService.updateCaracteristique(objetCaracteristique)
                .success(function (data) {
                })
                .catch(function (error) {
                    showErrorToast("Une erreur est survenue lors de la création");
                });
        }

        function insertEmplacement(idObjet) {
            var emplacement = {
                id: null,
                objetId: idObjet,
                paramEmplId: $scope.valeurEmplacement.id,
                qteTotal: $scope.qteTotal
            };

            if($scope.toModify){
                emplacement.id = $scope.objectModify.emplacement.id;
            }
            appService.updateEmplacement(emplacement)
                .success(function (data) {
                    $scope.idEmplacement = data.id;
                    console.log($scope.objectModify);
                    insertTailles(idObjet);
                })
                .catch(function (error) {
                    showErrorToast("Une erreur est survenue lors de la création");
                })
        }

        function insertTailles(idObjet) {
            var list = $scope.gridApi.core.getVisibleRows();
            
            list.forEach(function (data) {
                if (data.entity.qte != null) {
                    var objetCaracteristique = {
                        objetId: idObjet,
                        caracteristique: "TAILLE",
                        valeur: data.entity.taille
                    };
                 
                    appService.insertCaracteristique(objetCaracteristique)
                        .success(function (data) {
                        })
                        .catch(function (error) {
                            showErrorToast("Une erreur est survenue lors de la création");
                        });
                    
                    var detailEmplacement = {
                        emplacementId: $scope.idEmplacement,
                        caracteristique: "TAILLE",
                        valeurCaracteristique: data.entity.taille,
                        qteEnCours: data.entity.qte
                    };
                    appService.insertDetailEmplacement(detailEmplacement)
                        .success(function (data) {
                        })
                        .catch(function (error) {
                            showErrorToast("Une erreur est survenue lors de la création")
                        })
                }
            });
            
            if($scope.toModify){
            	//suppression de toutes les tailles
                $scope.objectModify.tailles.forEach(function (taille) {
                    removeCharacteristics(taille);
                });
                
                //suppression du detail emplacement
                $scope.objectModify.detailEmplacement.forEach(function (detail) {
                	console.log('Remobe detail emplacement !!!');
                    removeDetailBox(detail);
                });
            }
                  
        }

        function goToHome() {
            $location.path("/retour");
            $localstorage.removeCanAddNewBox();
            $localstorage.removeObjectModify();
            window.parent.$("body").scrollTop(0);

            var myIFRame = window.parent.$("iframe#appIceberg");
            var valueS = $("#scroll0").val();
            var positionS = $("#position0").val();
            myIFRame.attr('height', valueS + 'px');
            //scroll position
            window.parent.$("body").scrollTop(positionS);
        }

        function showDialogNewBox(ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            if ($scope.canAddNewBox) {
                var confirm = $mdDialog.confirm()
                    .textContent('Souhaites-tu créer un nouvel emplacement ?')
                    .ariaLabel('Helvetica Neue')
                    .targetEvent(ev)
                    .ok('oui')
                    .cancel('non');

                $mdDialog.show(confirm).then(function () {
                    $scope.addNewBox();
                }, function () {
                    $scope.status = ' ';
                });
            } else {

                var confirm = $mdDialog.alert()
                    .title('Emplacement')
                    .textContent('Tu as déjà créé un nouvel emplacement !' +
                        ' Tu ne peux en créer un deuxième')
                    .ariaLabel('Helvetica Neue')
                    .targetEvent(ev)
                    .ok('ok');

                $mdDialog.show(confirm).then(function () {
                }, function () {
                    $scope.status = ' ';
                });
            }
        };

        function addNewBox() {
            var tmp = 0;
            var box;
            appService.getAllEmplacement().success(function (data) {
                $localstorage.saveEmplacements(data);
                $scope.listEmplacements = data;
                data.forEach(function (empl) {
                    var ref = Number(empl.ref);
                    tmp = tmp < ref ? ref : tmp;
                });
                tmp = tmp + 1;
                appService.createParamEmpl({
                    ref: tmp,
                    libelle: 'Boite ' + tmp
                }).success(function (data) {
                    box = data;
                    appService.getAllEmplacement().success(function (data) {
                        $localstorage.saveEmplacements(data);
                        $scope.listEmplacements = data;
                        $scope.displayModal = false;
                        $localstorage.saveCanAddNewBox(box);
                        $scope.canAddNewBox = false;
                        $scope.valeurEmplacement = box;
                    });
                });
            }).catch(function (error) {
                console.log(error);
                showErrorToast("Oups ... un pingouin n'a pas pu ramener ta boîte sur l'iceberg ! Essaie à nouveau");
            });
        }

        function closedModalNewBox() {
            this.displayModal = false;
        }

        function showInfoToast(information){
            var toast = $mdToast.simple()
                .textContent(information)
                .position('top right')
                .hideDelay(5000)
                .toastClass("info-toast");

            $mdToast.show(toast);
        }

        function showWarningToast(information){
            var toast = $mdToast.simple()
                .textContent(information)
                .position('top right')
                .hideDelay(5000)
                .toastClass("warning-toast");

            $mdToast.show(toast);
        }

        function showErrorToast(information){
            var toast = $mdToast.simple()
                .textContent(information)
                .position('top right')
                .hideDelay(5000)
                .toastClass("error-toast");

            $mdToast.show(toast);
        }

        function removeCharacteristics(characteristic){
            appService.removeCharacteristic(characteristic.id).success(function (data) {
            	console.log('OK suppression !');
            })
        }

        function removeDetailBox(detailBox){
            appService.removeDetailBox(detailBox.id).success(
            		function (data) {
                    	console.log('OK suppression boite !');
                    })
        }
    }
})();
