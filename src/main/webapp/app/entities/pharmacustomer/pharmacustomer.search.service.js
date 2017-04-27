(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .factory('PharmacustomerSearch', PharmacustomerSearch);

    PharmacustomerSearch.$inject = ['$resource'];

    function PharmacustomerSearch($resource) {
        var resourceUrl =  'api/_search/pharmacustomers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
