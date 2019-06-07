(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('DetailEmplacement', DetailEmplacement);

    DetailEmplacement.$inject = ['$resource'];

    function DetailEmplacement ($resource) {
        var resourceUrl =  'api/detail-emplacements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
