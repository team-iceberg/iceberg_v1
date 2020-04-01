(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamCouleurDetailController', ParamCouleurDetailController);

    ParamCouleurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParamCouleur'];

    function ParamCouleurDetailController($scope, $rootScope, $stateParams, previousState, entity, ParamCouleur) {
        var vm = this;

        vm.paramCouleur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:paramCouleurUpdate', function(event, result) {
            vm.paramCouleur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
