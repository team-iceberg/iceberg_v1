(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTypeDialogController', ParamTypeDialogController);

    ParamTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParamType'];

    function ParamTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParamType) {
        var vm = this;

        vm.paramType = entity;
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
            if (vm.paramType.id !== null) {
                ParamType.update(vm.paramType, onSaveSuccess, onSaveError);
            } else {
                ParamType.save(vm.paramType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:paramTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
