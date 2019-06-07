//controler
(function () {
    'use strict';

    //controler for detail
    angular.module('app').controller('appControllerResaEnCours', appControllerResaEnCours);
    appControllerResaEnCours.$inject = ['appService', '$scope', '$localstorage', '$location', '$mdToast'];

    function appControllerResaEnCours(appService, $scope, $localstorage, $location, $mdToast) {

        var vm = this;
        vm.activated;
        $scope.formatDate = formatDate;

        init();

        function init() {
            vm.activated = true;
            vm.account = $localstorage.getUser();
            appService.getResaEnCours().success(function (data) {
                vm.detail = data;
                vm.nbResaEnCours = data.length;
                calculateScrollBar(data);
                vm.activated = false;
            }).error(function () {
                $mdToast.show({
                    hideDelay: 7000,
                    position: 'top right',
                    toastClass: 'warning-toast',
                    template: '<md-toast>\n' +
                    '  <span class="md-toast-text" flex>Une erreur est survenue lors du chargement des données</span>\n' +
                    '</md-toast>'
                });
                vm.activated = false;
            });
        }

        function calculateScrollBar(data) {
            //scroll
            var largeur_fenetre = $(window.parent).width();
            var nbElt = 5;
            if (largeur_fenetre < 750)
                nbElt = 1;
            else if (largeur_fenetre < 1170)
                nbElt = 2;
            var myIFRame = window.parent.$("iframe#appIceberg");
            var pixel = Math.ceil(data.length / nbElt) * 315 + 295;
            if (data.length == 0)
                pixel = 375;
            myIFRame.attr('height', pixel + 'px');
        }

        $scope.loadScrollSearch = function () {
            var myIFRame = window.parent.$("iframe#appIceberg");
            var valueS = $("#scroll0").val();
            var positionS = $("#position0").val();
            myIFRame.attr('height', valueS + 'px');
            //scroll position
            window.parent.$("body").scrollTop(positionS);
        };

        $scope.goToBookingDetail = function (object) {
            $localstorage.saveObjectBooked(object);
            $location.path("/booking-detail");
        };

        function formatDate(date) {
            var d = new Date(date);
            return d.getDate() + '/' + (d.getMonth()+1) + '/' + d.getFullYear();
        }
    }
})();
