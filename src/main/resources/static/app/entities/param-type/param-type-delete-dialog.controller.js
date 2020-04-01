(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTypeDeleteController',ParamTypeDeleteController);

    ParamTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParamType'];

    function ParamTypeDeleteController($uibModalInstance, entity, ParamType) {
        var vm = this;

        vm.paramType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParamType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
