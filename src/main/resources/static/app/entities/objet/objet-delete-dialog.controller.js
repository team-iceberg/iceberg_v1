(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetDeleteController',ObjetDeleteController);

    ObjetDeleteController.$inject = ['$uibModalInstance', 'entity', 'Objet'];

    function ObjetDeleteController($uibModalInstance, entity, Objet) {
        var vm = this;

        vm.objet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Objet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
