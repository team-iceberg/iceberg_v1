(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTailleDialogController', ParamTailleDialogController);

    ParamTailleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParamTaille'];

    function ParamTailleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParamTaille) {
        var vm = this;

        vm.paramTaille = entity;
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
            if (vm.paramTaille.id !== null) {
                ParamTaille.update(vm.paramTaille, onSaveSuccess, onSaveError);
            } else {
                ParamTaille.save(vm.paramTaille, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:paramTailleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
