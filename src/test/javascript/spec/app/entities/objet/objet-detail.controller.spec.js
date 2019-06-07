'use strict';

describe('Controller Tests', function() {

    describe('Objet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockObjet, MockEmplacement, MockObjetCaracteristiques, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockObjet = jasmine.createSpy('MockObjet');
            MockEmplacement = jasmine.createSpy('MockEmplacement');
            MockObjetCaracteristiques = jasmine.createSpy('MockObjetCaracteristiques');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Objet': MockObjet,
                'Emplacement': MockEmplacement,
                'ObjetCaracteristiques': MockObjetCaracteristiques,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ObjetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'icebergApp:objetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
