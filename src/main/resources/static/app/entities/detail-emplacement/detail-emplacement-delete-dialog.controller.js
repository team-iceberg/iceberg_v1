(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('DetailEmplacementDeleteController',DetailEmplacementDeleteController);

    DetailEmplacementDeleteController.$inject = ['$uibModalInstance', 'entity', 'DetailEmplacement'];

    function DetailEmplacementDeleteController($uibModalInstance, entity, DetailEmplacement) {
        var vm = this;

        vm.detailEmplacement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DetailEmplacement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
