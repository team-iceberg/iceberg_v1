(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamCouleurDialogController', ParamCouleurDialogController);

    ParamCouleurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParamCouleur'];

    function ParamCouleurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParamCouleur) {
        var vm = this;

        vm.paramCouleur = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paramCouleur.id !== null) {
                ParamCouleur.update(vm.paramCouleur, onSaveSuccess, onSaveError);
            } else {
                ParamCouleur.save(vm.paramCouleur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:paramCouleurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
