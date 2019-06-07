(function () {
    'use strict';

    angular.module('app').controller('appControllerReturn', appControllerReturn);
    appControllerReturn.$inject = ['$scope', '$localstorage', '$location', 'appService', '$mdDialog', 'uiGridConstants', '$mdToast'];

    function appControllerReturn($scope, $localstorage, $location, appService, $mdDialog, uiGridConstants, $mdToast) {

        var vm = this;

        $scope.objectBooked = null;

        $scope.gridTailles = {};

        init();

        function init() {
            $scope.objectBooked = $localstorage.getObjectBooked();
            var myIFRame = window.parent.$("iframe#appIceberg");
            myIFRame.scrollTop();
            var largeur_fenetre = $(window.parent).width();
            var hauteur = 600;
            if (largeur_fenetre < 750)
                hauteur = 800;
            else if (largeur_fenetre < 1170)
                hauteur = 700;
            myIFRame.attr('height', hauteur + 'px');
            window.parent.$("body").scrollTop(0);

            initGridTailles();
        }

        $scope.goToReservationList = function () {
            $location.path('resaEnCours');
        };

        $scope.returnObject = function () {
            var i;
            var listDetailEmplacement = [];
            for (i = 0; i < $scope.objectBooked.lesReservationsEnCours.length; i++) {
                $scope.objectBooked.lesReservationsEnCours[i].qteRet = $scope.objectBooked.lesReservationsEnCours[i].qteResa;
                $scope.objectBooked.lesReservationsEnCours[i].detailEmplacement.qteEnCours = $scope.objectBooked.lesReservationsEnCours[i].detailEmplacement.qteEnCours + $scope.objectBooked.lesReservationsEnCours[i].qteRet;
                listDetailEmplacement.push($scope.objectBooked.lesReservationsEnCours[i].detailEmplacement);
            }

            appService.updateReservationsList($scope.objectBooked.lesReservationsEnCours).success(function (data) {
                updateDetailEmpl(listDetailEmplacement);
            }).catch(function (error) {
                console.log(error);
            });

        };

        function updateDetailEmpl(listDetailEmpl) {
            appService.updateQtyDetailObject(listDetailEmpl).success(function (data) {

                console.log(data);
                var info = $mdDialog.alert()
                    .title('PRODUIT BIEN RENDU')
                    .textContent("Attention à bien remettre tous tes produits dans l'emplacement " + $scope.objectBooked.lesReservationsEnCours[0].detailEmplacement.emplacement.paramEmpl.libelle)
                    .ariaLabel('Helvetica Neue')
                    .ok("ok, j'ai bien compris");

                $mdDialog.show(info).then(function () {
                    $location.path('resaEnCours');
                }, function () {
                    $location.path('resaEnCours');
                });

            }).catch(function (error) {
                console.log(error);
                $mdToast.show({
                    hideDelay: 7000,
                    position: 'top right',
                    toastClass: 'error-toast',
                    template: '<md-toast>\n' +
                    '  <span class="md-toast-text" flex>Une erreur est survenue lors du rendu du produit</span>\n' +
                    '</md-toast>'
                });
            });
        }

        function initGridTailles() {
            $scope.gridTailles.rowHeight = 30;
            $scope.gridTailles.enableColumnMenus = false;
            $scope.gridTailles.showColumnFooter = true;
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
                    field: 'qte',
                    displayName: 'quantité',
                    enableFiltering: true,
                    enableCellEdit: false,
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

        function initDataToGrid() {
            var result = [];

            var i;
            for (i = 0; i < $scope.objectBooked.lesReservationsEnCours.length; i++) {
                var object = {};
                object.valeurCaracteristique = $scope.objectBooked.lesReservationsEnCours[i].detailEmplacement.valeurCaracteristique;
                object.qte = $scope.objectBooked.lesReservationsEnCours[i].qteResa;

                result.push(object);
            }
            return result.sort(function (length1, length2) {
                if (length1.valeurCaracteristique < length2.valeurCaracteristique)
                    return -1;
                if (length1.valeurCaracteristique > length2.valeurCaracteristique)
                    return 1;
                return 0;
            });
        }
    }
})();
