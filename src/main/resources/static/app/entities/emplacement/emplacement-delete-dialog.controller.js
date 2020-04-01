(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('EmplacementDeleteController',EmplacementDeleteController);

    EmplacementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Emplacement'];

    function EmplacementDeleteController($uibModalInstance, entity, Emplacement) {
        var vm = this;

        vm.emplacement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Emplacement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
