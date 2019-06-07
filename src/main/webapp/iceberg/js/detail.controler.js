//controler
(function () {
    'use strict';

    angular.module('app').factory("sharedContext", function () {
        var monObjet;
        var addData = function (data) {
            monObjet = data;
        }
        var getData = function () {
            return monObjet;
        }

        return {
            addData: addData,
            getData: getData
        }
    });

    //controler for detail
    angular.module('app').controller('appControllerDetail', appControllerDetail);
    appControllerDetail.$inject = ['$route', 'appService', '$scope', '$localstorage', 'uiGridConstants', '$mdDialog', '$location', 'sharedContext', '$mdToast'];

    function appControllerDetail($route, appService, $scope, $localstorage, uiGridConstants, $mdDialog, $location, sharedContext, $mdToast) {

        var vm = this;

        vm.account = $localstorage.getUser();

        $scope.gridTailles = {};
        $scope.isDeletable = false;
        $scope.isModify = false;

        $scope.confirmDelete = function (ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .textContent('Etes-vous sûr de supprimer ce produit ?')
                .ariaLabel('Helvetica Neue')
                .targetEvent(ev)
                .ok('oui')
                .cancel('non');

            $mdDialog.show(confirm).then(function () {
                vm.deleteObjet();
                $location.path("/retour");

                var myIFRame = window.parent.$("iframe#appIceberg");
                var valueS = $("#scroll0").val();

                var positionS = $("#position0").val();
                myIFRame.attr('height', valueS + 'px');
                //scroll position
                window.parent.$("body").scrollTop(positionS);

            }, function () {
                $scope.status = ' ';
            });
        };

        appService.getMyObjet($route.current.params.id).success(function (data) {
            vm.detail = data;
            sharedContext.addData(data);
            var myIFRame = window.parent.$("iframe#appIceberg");
            myIFRame.scrollTop();
            var largeur_fenetre = $(window.parent).width();
            var hauteur = 600;
            if (largeur_fenetre < 750)
                hauteur = 850;
            else if (largeur_fenetre < 1170)
                hauteur = 700;
            myIFRame.attr('height', hauteur + 'px');
            window.parent.$("body").scrollTop(0);
            initGridTailles();
            initColor();
            initType();
        })
            .error(function () {
                var myIFRame = window.parent.$("iframe#appIceberg");
                myIFRame.attr('height', 400 + 'px');

                var alerte = $mdDialog.alert()
                    .textContent("Ce produit n'est plus dans l'iceberg !")
                    .ariaLabel('Helvetica Neue')
                    .ok('OK');

                $mdDialog.show(alerte).then(function () {
                    $location.path("/retour");
                    var myIFRame = window.parent.$("iframe#appIceberg");
                    var valueS = $("#scroll0").val();

                    var positionS = $("#position0").val();
                    myIFRame.attr('height', valueS + 'px');
                    //scroll position
                    window.parent.$("body").scrollTop(positionS);
                });
            });

        function initGridTailles() {
            $scope.gridTailles.rowHeight = 30;
            $scope.gridTailles.showColumnFooter = true;
            $scope.gridTailles.enableColumnMenus = false;
            $scope.gridTailles.enableHorizontalScrollbar = 0;
            $scope.gridTailles.columnDefs = [
                {
                    field: 'valeurCaracteristique',
                    displayName: 'taille',
                    enableFiltering: true,
                    pinnedLeft: true,
                    enableCellEdit: false,
                    width: 90,
                    cellClass: 'center',
                    headerCellClass: "center",
                    footerCellTemplate: '<div class="ui-grid-cell-contents center">Total</div>'
                },
                {
                    field: 'qteReel',
                    displayName: 'en stock',
                    enableFiltering: true,
                    enableCellEdit: true,
                    type: 'number',
                    width: 90,
                    cellClass: 'center',
                    headerCellClass: "center",
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    footerCellTemplate: '<div class="ui-grid-cell-contents center">{{col.getAggregationValue()}}</div>'
                },
                {
                    field: 'qteResa',
                    displayName: 'réservés',
                    enableFiltering: true,
                    enableCellEdit: true,
                    type: 'number',
                    width: 90,
                    cellClass: 'center',
                    headerCellClass: "center",
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    footerCellTemplate: '<div class="ui-grid-cell-contents center">{{col.getAggregationValue()}}</div>'
                }
            ];
            $scope.gridTailles.data = initDataToGrid();
        }

        $scope.myfunction = function (aggRow) {
            // do stuff to aggRow
            return 789;
        }

        function initColor() {
            var colorList = $localstorage.getCouleurs();
            if (colorList == null || colorList == undefined) {
                appService.getAllCouleurs().success(function (data) {
                    $localstorage.saveCouleurs(data);
                    colorList = data;
                    vm.colorResult = colorList.find(function (color) {
                        return color.couleur == vm.detail.couleur.valeur;
                    })
                });
            } else {
                vm.colorResult = colorList.find(function (color) {
                    return color.couleur == vm.detail.couleur.valeur;
                })
            }
        }
        
        function initType() {
            var typesList = $localstorage.getTypes();
            if (typesList == null || typesList == undefined) {
                appService.getAllTypes().success(function (data) {
                    $localstorage.saveTypes(data);
                    typesList = data;
                    vm.typesResult = typesList.find(function (type) {
                        return type.type == vm.detail.type.valeur;
                    })
                });
            } else {
                vm.typesResult = typesList.find(function (type) {
                    return type.type == vm.detail.type.valeur;
                })
            }
        }

        function initDataToGrid() {
            var result = [];
            var qteResaTotal = 0;

            vm.detail.detailEmplacement.forEach(function (object) {
                object.qteReel = object.qteEnCours;
                //avec quantité résa ?
                var qteResa = 0;

                vm.detail.lesReservations.forEach(function (resa) {
                    if (resa.detailEmplacement.valeurCaracteristique == object.valeurCaracteristique) {
                        qteResa = qteResa + resa.qteResa;
                    }
                });
                qteResaTotal += qteResa;

                object.qteResa = qteResa;
                if (object.valeurCaracteristique != 'TOTAL') {
                    result.push(object);
                }
            });

            if(qteResaTotal == 0){
                $scope.isDeletable = true;
                $scope.isModify = true;
            }

            return result.sort(function (length1, length2) {
                if (length1.valeurCaracteristique < length2.valeurCaracteristique)
                    return -1;
                if (length1.valeurCaracteristique > length2.valeurCaracteristique)
                    return 1;
                return 0;
            });
        }

        vm.deleteObjet = function () {
            appService.deleteObjet(vm.detail.id).success(function (data) {
                console.log('Suppression OK');
                showInfoToast("Ton produit a bien été supprimé");
            });
        };

        function showInfoToast(information){
            var toast = $mdToast.simple()
                .textContent(information)
                .position('top right')
                .hideDelay(5000)
                .toastClass("info-toast");

            $mdToast.show(toast);
        }

        $scope.modifyProduct = function (event){
            $localstorage.saveObjectModify(vm.detail);
            $location.path("/creer");
        }

    }
})();
