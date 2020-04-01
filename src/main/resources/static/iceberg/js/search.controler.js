//controler
(function () {
    'use strict';

    var app = angular.module('app');
    app.controller('appController', appController);
    appController.$inject = ['$http', 'appService', '$scope', '$localstorage', '$location'];

    app.controller('SelectHeaderEmplacementController', function ($scope, $element) {
        $scope.searchTerm;
        $scope.clearSearchTerm = function () {
            $scope.searchTerm = '';
        };
        // The md-select directive eats keydown events for some quick select
        // logic. Since we have a search input here, we don't need that logic.
        $element.find('input').on('keydown', function (ev) {
            ev.stopPropagation();
        });
    });


    function appController($http, appService, $scope, $localstorage, $location) {
        var vm = this;

        $scope.searchTerm;

        init();

        function init() {
            vm.account = $localstorage.getUser();
            $localstorage.removeObjectModify();
            if(vm.account === null || vm.account === undefined){
                appService.getAccount().success(function (data) {
                    $localstorage.saveUser(data);
                    vm.account = data;
                });
            }
        }

        $scope.toPositionOnSearch = function () {
            var position = window.parent.$("body").scrollTop();
            $("#position0").attr('value', position);
        };

        $scope.loadScrollSearch = function () {
            var myIFRame = window.parent.$("iframe#appIceberg");
            var valueS = $("#scroll0").val();
            console.log(valueS);
            var positionS = $("#position0").val();
            myIFRame.attr('height', valueS + 'px');
            //scroll position
            window.parent.$("body").scrollTop(positionS);
        };

        $scope.clickOnCouleurs = function () {
            vm.listCouleurs = $localstorage.getCouleurs();
            if (vm.listCouleurs == null || vm.listCouleurs == undefined) {
                appService.getAllCouleurs().success(function (data) {
                    $localstorage.saveCouleurs(data);
                    vm.listCouleurs = data;
                });
            }
        };

        $scope.clickOnTypes = function () {
            vm.listTypes = $localstorage.getTypes();
            if (vm.listTypes == null || vm.listTypes == undefined) {
                appService.getAllTypes().success(function (data) {
                    $localstorage.saveTypes(data);
                    vm.listTypes = data;
                });
            }
        };

        $scope.clickOnTailles = function () {
            vm.listTailles = $localstorage.getTailles();
            if (vm.listTailles == null || vm.listTailles == undefined) {
                appService.getAllTailles().success(function (data) {
                    $localstorage.saveTailles(data);
                    vm.listTailles = data;
                });
            }
        };

        $scope.clickOnEmplacement = function () {
            if ($scope.listEmplacements == null || $scope.listEmplacements == undefined) {
                appService.getAllEmplacement().success(function (data) {
                    $localstorage.saveEmplacements(data);
                    $scope.listEmplacements = data;
                    vm.listEmplacements = data;
                });
            }
        };


        $scope.clearSearchTerm = function () {
            $scope.searchTerm = '';
        };
        // The md-select directive eats keydown events for some quick select
        // logic. Since we have a search input here, we don't need that logic.

        vm.lesObjets = function () {
            var vType = '%40';
            if (vm.ref != null && vm.ref.type != null && vm.ref.type != 'Tous')
                vType = vm.ref.type;
            var vCouleur = '%40';
            if (vm.ref != null && vm.ref.couleur != null && vm.ref.couleur != 'Tous')
                vCouleur = vm.ref.couleur;
            var vTaille = '%40';
            if (vm.ref != null && vm.ref.taille != null && vm.ref.taille.length > 0)
                vTaille = vm.ref.taille.toString();
            var vEmpl = '%40';
            if (vm.ref != null && vm.ref.emplacement != null && vm.ref.emplacement != 'Tous')
                vEmpl = vm.ref.emplacement.toString();

            vm.showList = true;
            vm.determinateValue = 0;
            vm.determinateValue2 = 0;
            appService.getObjet(1, vType, vCouleur, vTaille, vEmpl).success(function (data) {
                vm.resultObjets = data;
                vm.showList = false;
                vm.nbEltFound = data.length;
                if (data.length > 0)
                    vm.nbEltTotal = data[0].nbEltsTotal;
                else
                    vm.nbEltTotal = 0;

                //scroll
                var largeur_fenetre = $(window.parent).width();
                var nbElt = 5;
                if (largeur_fenetre < 750)
                    nbElt = 1;
                else if (largeur_fenetre < 1170)
                    nbElt = 2;
                var myIFRame = window.parent.$("iframe#appIceberg");
                var pixel = Math.ceil(data.length / nbElt) * 288 + 290;
                if (data.length == 0)
                    pixel = 375;
                myIFRame.attr('height', pixel + 'px');
                $("#scroll0").attr('value', pixel);
            })
        };

        vm.filtrerParEmplacement = function () {
            $("#filtreEmplacement").css({"visibility": "visible"});
            $("#filtreCouleur").css({"visibility": "hidden"});
            $("#filtreType").css({"visibility": "hidden"});
            $("#filtreTaille").css({"visibility": "hidden"});
            $("#clickFiltrerParColTaiType").css({"visibility": "visible"});
            $("#clickFiltrerParEmplacement").css({"visibility": "hidden"});
            if (vm.ref != null) {
                vm.ref.couleur = null;
                vm.ref.type = null;
                vm.ref.taille = null;
            }
        };

        vm.filtrerParColTaiType = function () {
            $("#filtreEmplacement").css({"visibility": "hidden"});
            $("#filtreCouleur").css({"visibility": "visible"});
            $("#filtreType").css({"visibility": "visible"});
            $("#filtreTaille").css({"visibility": "visible"});
            $("#clickFiltrerParColTaiType").css({"visibility": "hidden"});
            $("#clickFiltrerParEmplacement").css({"visibility": "visible"});
            if (vm.ref != null) {
                vm.ref.emplacement = null;
            }
        };

        vm.goToCreation = function () {
            $location.path("/creer");
        };
    }

})();

