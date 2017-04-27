(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enterprisecustomer', {
            parent: 'entity',
            url: '/enterprisecustomer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.enterprisecustomer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomers.html',
                    controller: 'EnterprisecustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('enterprisecustomer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('enterprisecustomer-detail', {
            parent: 'enterprisecustomer',
            url: '/enterprisecustomer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.enterprisecustomer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomer-detail.html',
                    controller: 'EnterprisecustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('enterprisecustomer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Enterprisecustomer', function($stateParams, Enterprisecustomer) {
                    return Enterprisecustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enterprisecustomer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enterprisecustomer-detail.edit', {
            parent: 'enterprisecustomer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomer-dialog.html',
                    controller: 'EnterprisecustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enterprisecustomer', function(Enterprisecustomer) {
                            return Enterprisecustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enterprisecustomer.new', {
            parent: 'enterprisecustomer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomer-dialog.html',
                    controller: 'EnterprisecustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                enterprisecustomernr: null,
                                pharmaacctnr: null,
                                medicalacctnr: null,
                                address: null,
                                city: null,
                                state: null,
                                zipcode: null,
                                country: null,
                                pharmaShipTo: null,
                                pharmabillto: null,
                                medicalshipto: null,
                                dealicensenr: null,
                                healthfacilitylicensenr: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enterprisecustomer', null, { reload: 'enterprisecustomer' });
                }, function() {
                    $state.go('enterprisecustomer');
                });
            }]
        })
        .state('enterprisecustomer.edit', {
            parent: 'enterprisecustomer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomer-dialog.html',
                    controller: 'EnterprisecustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enterprisecustomer', function(Enterprisecustomer) {
                            return Enterprisecustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enterprisecustomer', null, { reload: 'enterprisecustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enterprisecustomer.delete', {
            parent: 'enterprisecustomer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enterprisecustomer/enterprisecustomer-delete-dialog.html',
                    controller: 'EnterprisecustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Enterprisecustomer', function(Enterprisecustomer) {
                            return Enterprisecustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enterprisecustomer', null, { reload: 'enterprisecustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
