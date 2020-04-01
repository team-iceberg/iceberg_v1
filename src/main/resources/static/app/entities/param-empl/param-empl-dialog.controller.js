(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamEmplDialogController', ParamEmplDialogController);

    ParamEmplDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ParamEmpl'];

    function ParamEmplDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ParamEmpl) {
        var vm = this;

        vm.paramEmpl = entity;
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
            if (vm.paramEmpl.id !== null) {
                ParamEmpl.update(vm.paramEmpl, onSaveSuccess, onSaveError);
            } else {
                ParamEmpl.save(vm.paramEmpl, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:paramEmplUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
