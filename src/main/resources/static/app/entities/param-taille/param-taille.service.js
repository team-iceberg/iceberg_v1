(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('ParamTaille', ParamTaille);

    ParamTaille.$inject = ['$resource'];

    function ParamTaille ($resource) {
        var resourceUrl =  'api/param-tailles/:id';

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
