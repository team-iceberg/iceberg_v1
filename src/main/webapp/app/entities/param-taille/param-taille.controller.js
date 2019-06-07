(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTailleController', ParamTailleController);

    ParamTailleController.$inject = ['$scope', '$state', 'ParamTaille'];

    function ParamTailleController ($scope, $state, ParamTaille) {
        var vm = this;

        vm.paramTailles = [];

        loadAll();

        function loadAll() {
            ParamTaille.query(function(result) {
                vm.paramTailles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
