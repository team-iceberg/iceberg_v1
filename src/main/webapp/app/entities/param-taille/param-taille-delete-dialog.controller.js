(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTailleDeleteController',ParamTailleDeleteController);

    ParamTailleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParamTaille'];

    function ParamTailleDeleteController($uibModalInstance, entity, ParamTaille) {
        var vm = this;

        vm.paramTaille = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParamTaille.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
