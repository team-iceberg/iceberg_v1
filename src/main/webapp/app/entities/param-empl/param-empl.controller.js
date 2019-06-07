(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamEmplController', ParamEmplController);

    ParamEmplController.$inject = ['$scope', '$state', 'ParamEmpl'];

    function ParamEmplController ($scope, $state, ParamEmpl) {
        var vm = this;

        vm.paramEmpls = [];

        loadAll();

        function loadAll() {
            ParamEmpl.query(function(result) {
                vm.paramEmpls = result;
                vm.searchQuery = null;
            });
        }
    }
})();
