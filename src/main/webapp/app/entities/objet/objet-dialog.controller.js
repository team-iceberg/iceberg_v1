(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetDialogController', ObjetDialogController);

    ObjetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Objet', 'Emplacement', 'ObjetCaracteristiques', 'User'];

    function ObjetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Objet, Emplacement, ObjetCaracteristiques, User) {
        var vm = this;

        vm.objet = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.emplacements = Emplacement.query();
        vm.objetcaracteristiques = ObjetCaracteristiques.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.objet.id !== null) {
                Objet.update(vm.objet, onSaveSuccess, onSaveError);
            } else {
                Objet.save(vm.objet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('icebergApp:objetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDepot = false;

        vm.setImage1 = function ($file, objet) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        objet.image1 = base64Data;
                        objet.image1ContentType = $file.type;
                    });
                });
            }
        };

        vm.setImage2 = function ($file, objet) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        objet.image2 = base64Data;
                        objet.image2ContentType = $file.type;
                    });
                });
            }
        };

        vm.setImage3 = function ($file, objet) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        objet.image3 = base64Data;
                        objet.image3ContentType = $file.type;
                    });
                });
            }
        };

        vm.setImage4 = function ($file, objet) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        objet.image4 = base64Data;
                        objet.image4ContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
