//controler
(function () {
    'use strict';
    //controler for detail
    angular.module('app').controller('appControllerReserve', appControllerReserve);
    appControllerReserve.$inject = ['appService', '$scope', '$localstorage', '$mdDialog', '$location', 'sharedContext', '$mdToast'];

    function appControllerReserve(appService, $scope, $localstorage, $mdDialog, $location, sharedContext, $mdToast) {

        var vm = this;
        $scope.reserver = reserver;
        $scope.multiSelect = multiSelect;
        $scope.canceled = canceled;
        $scope.reservePar;
        $scope.listQtyResa = [];
        $scope.isChecked = false;

        vm.detail = sharedContext.getData();
        console.log(vm.detail);

        appService.getAllMono().success(function (data) {
            vm.allMono = data;
        });

        function reserver(ev) {
            console.log($scope.listQtyResa);
            if (formIsValid()) {
                var currentDate = new Date();
                var qteTotale = 0;
                var i;
                for (i = 0; i < vm.detail.detailEmplacement.length; i++) {
                    var qteResa = $scope.listQtyResa[vm.detail.detailEmplacement[i].id];
                    if (qteResa > 0) {
                        //control and ajust the stock
                        try {
                            ajusterQteEnCours(vm.detail.detailEmplacement[i].id, qteResa);
                        } catch (error) {
                            console.log(error);
                            continue;
                        }
                        qteTotale = parseInt(qteTotale) + parseInt(qteResa);
                        //create reservation
                        var resa = {
                            dateReservation: currentDate,
                            detailEmplacementId: vm.detail.detailEmplacement[i].id,
                            qteResa: qteResa,
                            qteRet: 0,
                            qui: $scope.reservePar
                        };
                        console.log(resa);
                        appService.insertReservations(resa)
                            .success(function (data) {
                                console.log(data);
                            })
                            .catch(function (error) {
                                console.log(error)
                            });
                    }
                }

                var info = $mdDialog.alert()
                    .title('Réservation validée !')
                    .textContent("Tu viens de réserver " + qteTotale + " produit(s) de l'emplacement " + vm.detail.detailEmplacement[0].emplacement.paramEmpl.libelle)
                    .ariaLabel('Helvetica Neue')
                    .targetEvent(ev)
                    .ok('ok');

                $mdDialog.show(info).then(function () {
                    goToHome();
                }, function () {
                    goToHome();
                });
            }
        }

        function ajusterQteEnCours(idDetailEmplacement, qteResa) {

            //recherche du niveau de stock
            appService.getMyDetailEmplacement(idDetailEmplacement)
                .success(function (data) {
                    if (data.qteEnCours < qteResa) {
                        throw new Error('Pas assez de stock disponible !')
                    }

                    data.qteEnCours = data.qteEnCours - qteResa;
                    appService.majDetailEmplacement(data)
                        .success(function (data) {
                        })

                })
                .catch(function (error) {
                    console.log(error)
                });
        }

        function goToHome() {
            $location.path("/retour");
            $localstorage.removeCanAddNewBox();
            window.parent.$("body").scrollTop(0);

            var myIFRame = window.parent.$("iframe#appIceberg");
            var valueS = $("#scroll0").val();
            var positionS = $("#position0").val();
            myIFRame.attr('height', valueS + 'px');
            //scroll position
            window.parent.$("body").scrollTop(positionS);
        }

        function formIsValid() {
            if ($scope.reservePar === undefined || $scope.reservePar === null) {
                $mdToast.show({
                    hideDelay: 7000,
                    position: 'top right',
                    toastClass: 'warning-toast',
                    template: '<md-toast>\n' +
                    '  <span class="md-toast-text" flex>Attention ! Tu dois renseigner qui réserve le costume</span>\n' +
                    '</md-toast>'
                });
                return false;
            }
            if ($scope.listQtyResa.length > 0) {
                var valueNull = 0;
                var valueUndefined = 0;

                $scope.listQtyResa.forEach(function (qtyResa) {
                    if (qtyResa === undefined) {
                        valueUndefined++;
                    }
                    if (qtyResa !== null && qtyResa !== undefined) {
                        valueNull++;
                    }
                });
                if (valueUndefined > 0) {
                    $mdToast.show({
                        hideDelay: 7000,
                        position: 'top right',
                        toastClass: 'warning-toast',
                        template: '<md-toast>\n' +
                        '  <span style="background: red" class="md-toast-text" flex>Quantité réservée inférieur ou supérieure à la quantité disponible</span>\n' +
                        '</md-toast>'
                    });
                    false;
                } else if (valueNull === 0) {
                    $mdToast.show({
                        hideDelay: 7000,
                        position: 'top right',
                        toastClass: 'warning-toast',
                        template: '<md-toast >\n' +
                        '  <span class="md-toast-text" flex>Au moins une quantité doit être réservée</span>\n' +
                        '</md-toast>'
                    });
                    false;
                } else {
                    return true;
                }
            } else {
                $mdToast.show({
                    hideDelay: 7000,
                    position: 'top right',
                    toastClass: 'warning-toast',
                    template: '<md-toast>\n' +
                    '  <span class="md-toast-text" flex>Au moins une quantité doit être saisie</span>\n' +
                    '</md-toast>'
                });
                return false;
            }
            return false;
        }

        function multiSelect(){
            if($scope.isChecked){
                var i;
                for (i = 0; i < vm.detail.detailEmplacement.length; i++) {
                    var detailParTailles = vm.detail.detailEmplacement[i];
                    $scope.listQtyResa[detailParTailles.id] = detailParTailles.qteEnCours;
                }
            } else {
                $scope.listQtyResa = [];
            }
        }

        function canceled() {
            $location.path("/detail/" + vm.detail.id);
            window.parent.$("body").scrollTop(0);

            var myIFRame = window.parent.$("iframe#appIceberg");
            var valueS = $("#scroll0").val();
            var positionS = $("#position0").val();
            myIFRame.attr('height', valueS + 'px');
            //scroll position
            window.parent.$("body").scrollTop(positionS);
        }
    }
})();
