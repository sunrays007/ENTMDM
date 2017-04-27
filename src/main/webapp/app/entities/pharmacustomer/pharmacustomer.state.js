(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pharmacustomer', {
            parent: 'entity',
            url: '/pharmacustomer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.pharmacustomer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomers.html',
                    controller: 'PharmacustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pharmacustomer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pharmacustomer-detail', {
            parent: 'pharmacustomer',
            url: '/pharmacustomer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.pharmacustomer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomer-detail.html',
                    controller: 'PharmacustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pharmacustomer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pharmacustomer', function($stateParams, Pharmacustomer) {
                    return Pharmacustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pharmacustomer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pharmacustomer-detail.edit', {
            parent: 'pharmacustomer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomer-dialog.html',
                    controller: 'PharmacustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pharmacustomer', function(Pharmacustomer) {
                            return Pharmacustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pharmacustomer.new', {
            parent: 'pharmacustomer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomer-dialog.html',
                    controller: 'PharmacustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pharmacustomernr: null,
                                address: null,
                                city: null,
                                state: null,
                                zipcode: null,
                                country: null,
                                pharmashipto: null,
                                pharmabillto: null,
                                dealicensenr: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pharmacustomer', null, { reload: 'pharmacustomer' });
                }, function() {
                    $state.go('pharmacustomer');
                });
            }]
        })
        .state('pharmacustomer.edit', {
            parent: 'pharmacustomer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomer-dialog.html',
                    controller: 'PharmacustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pharmacustomer', function(Pharmacustomer) {
                            return Pharmacustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pharmacustomer', null, { reload: 'pharmacustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pharmacustomer.delete', {
            parent: 'pharmacustomer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pharmacustomer/pharmacustomer-delete-dialog.html',
                    controller: 'PharmacustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pharmacustomer', function(Pharmacustomer) {
                            return Pharmacustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pharmacustomer', null, { reload: 'pharmacustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
