/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FriendRequestComponent from '@/entities/friend-request/friend-request.vue';
import FriendRequestClass from '@/entities/friend-request/friend-request.component';
import FriendRequestService from '@/entities/friend-request/friend-request.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('FriendRequest Management Component', () => {
    let wrapper: Wrapper<FriendRequestClass>;
    let comp: FriendRequestClass;
    let friendRequestServiceStub: SinonStubbedInstance<FriendRequestService>;

    beforeEach(() => {
      friendRequestServiceStub = sinon.createStubInstance<FriendRequestService>(FriendRequestService);
      friendRequestServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FriendRequestClass>(FriendRequestComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          friendRequestService: () => friendRequestServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      friendRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllFriendRequests();
      await comp.$nextTick();

      // THEN
      expect(friendRequestServiceStub.retrieve.called).toBeTruthy();
      expect(comp.friendRequests[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      friendRequestServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(friendRequestServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFriendRequest();
      await comp.$nextTick();

      // THEN
      expect(friendRequestServiceStub.delete.called).toBeTruthy();
      expect(friendRequestServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
