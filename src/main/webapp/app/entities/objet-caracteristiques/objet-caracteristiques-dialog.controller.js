(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetCaracteristiquesDialogController', ObjetCaracteristiquesDialogController);

    ObjetCaracteristiquesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ObjetCaracteristiques', 'Objet'];

    function ObjetCaracteristiquesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ObjetCaracteristiques, Objet) {
        var vm = this;

        vm.objetCaracteristiques = entity;
        vm.clear = clear;
        vm.save = save;
        vm.objets = Objet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.objetCaracteristiques.id !== null) {
                ObjetCaracteristiques.update(vm.objetCaracteristiques, onSaveSuccess, onSaveError);
            } else {
                ObjetCaracteristiques.save(vm.objetCaracteristiques, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:objetCaracteristiquesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
