(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTypeController', ParamTypeController);

    ParamTypeController.$inject = ['$scope', '$state', 'ParamType'];

    function ParamTypeController ($scope, $state, ParamType) {
        var vm = this;

        vm.paramTypes = [];

        loadAll();

        function loadAll() {
            ParamType.query(function(result) {
                vm.paramTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
