(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('EnterprisecustomerDeleteController',EnterprisecustomerDeleteController);

    EnterprisecustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Enterprisecustomer'];

    function EnterprisecustomerDeleteController($uibModalInstance, entity, Enterprisecustomer) {
        var vm = this;

        vm.enterprisecustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Enterprisecustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
