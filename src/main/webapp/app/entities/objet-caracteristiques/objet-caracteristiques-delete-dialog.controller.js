(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetCaracteristiquesDeleteController',ObjetCaracteristiquesDeleteController);

    ObjetCaracteristiquesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ObjetCaracteristiques'];

    function ObjetCaracteristiquesDeleteController($uibModalInstance, entity, ObjetCaracteristiques) {
        var vm = this;

        vm.objetCaracteristiques = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ObjetCaracteristiques.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
