(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .factory('EnterprisecustomerSearch', EnterprisecustomerSearch);

    EnterprisecustomerSearch.$inject = ['$resource'];

    function EnterprisecustomerSearch($resource) {
        var resourceUrl =  'api/_search/enterprisecustomers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
