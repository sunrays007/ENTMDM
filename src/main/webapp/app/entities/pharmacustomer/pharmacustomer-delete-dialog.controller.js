(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('PharmacustomerDeleteController',PharmacustomerDeleteController);

    PharmacustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pharmacustomer'];

    function PharmacustomerDeleteController($uibModalInstance, entity, Pharmacustomer) {
        var vm = this;

        vm.pharmacustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pharmacustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
