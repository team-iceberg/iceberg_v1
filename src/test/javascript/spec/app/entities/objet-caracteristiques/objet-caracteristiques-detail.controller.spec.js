'use strict';

describe('Controller Tests', function() {

    describe('ObjetCaracteristiques Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockObjetCaracteristiques, MockObjet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockObjetCaracteristiques = jasmine.createSpy('MockObjetCaracteristiques');
            MockObjet = jasmine.createSpy('MockObjet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ObjetCaracteristiques': MockObjetCaracteristiques,
                'Objet': MockObjet
            };
            createController = function() {
                $injector.get('$controller')("ObjetCaracteristiquesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'icebergApp:objetCaracteristiquesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
