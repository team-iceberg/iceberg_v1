(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('DetailEmplacementDetailController', DetailEmplacementDetailController);

    DetailEmplacementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DetailEmplacement', 'Emplacement', 'Reservation'];

    function DetailEmplacementDetailController($scope, $rootScope, $stateParams, previousState, entity, DetailEmplacement, Emplacement, Reservation) {
        var vm = this;

        vm.detailEmplacement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:detailEmplacementUpdate', function(event, result) {
            vm.detailEmplacement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
