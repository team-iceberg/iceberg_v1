(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('Objet', Objet);

    Objet.$inject = ['$resource', 'DateUtils'];

    function Objet ($resource, DateUtils) {
        var resourceUrl =  'api/objets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDepot = DateUtils.convertDateTimeFromServer(data.dateDepot);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
