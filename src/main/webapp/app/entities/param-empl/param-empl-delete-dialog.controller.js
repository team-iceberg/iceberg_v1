(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamEmplDeleteController',ParamEmplDeleteController);

    ParamEmplDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParamEmpl'];

    function ParamEmplDeleteController($uibModalInstance, entity, ParamEmpl) {
        var vm = this;

        vm.paramEmpl = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParamEmpl.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
