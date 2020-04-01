(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('DetailEmplacementDialogController', DetailEmplacementDialogController);

    DetailEmplacementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DetailEmplacement', 'Emplacement', 'Reservation'];

    function DetailEmplacementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DetailEmplacement, Emplacement, Reservation) {
        var vm = this;

        vm.detailEmplacement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.emplacements = Emplacement.query();
        vm.reservations = Reservation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.detailEmplacement.id !== null) {
                DetailEmplacement.update(vm.detailEmplacement, onSaveSuccess, onSaveError);
            } else {
                DetailEmplacement.save(vm.detailEmplacement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:detailEmplacementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
