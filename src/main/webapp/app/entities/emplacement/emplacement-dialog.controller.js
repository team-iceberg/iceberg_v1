(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('EmplacementDialogController', EmplacementDialogController);

    EmplacementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Emplacement', 'Objet', 'DetailEmplacement', 'ParamEmpl'];

    function EmplacementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Emplacement, Objet, DetailEmplacement, ParamEmpl) {
        var vm = this;

        vm.emplacement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.objets = Objet.query();
        vm.detailemplacements = DetailEmplacement.query();
        vm.paramempls = ParamEmpl.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.emplacement.id !== null) {
                Emplacement.update(vm.emplacement, onSaveSuccess, onSaveError);
            } else {
                Emplacement.save(vm.emplacement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:emplacementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
