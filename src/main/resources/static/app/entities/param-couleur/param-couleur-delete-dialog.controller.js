(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamCouleurDeleteController',ParamCouleurDeleteController);

    ParamCouleurDeleteController.$inject = ['$uibModalInstance', 'entity', 'ParamCouleur'];

    function ParamCouleurDeleteController($uibModalInstance, entity, ParamCouleur) {
        var vm = this;

        vm.paramCouleur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ParamCouleur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
