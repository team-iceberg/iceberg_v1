(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamCouleurController', ParamCouleurController);

    ParamCouleurController.$inject = ['$scope', '$state', 'ParamCouleur'];

    function ParamCouleurController ($scope, $state, ParamCouleur) {
        var vm = this;

        vm.paramCouleurs = [];

        loadAll();

        function loadAll() {
            ParamCouleur.query(function(result) {
                vm.paramCouleurs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
