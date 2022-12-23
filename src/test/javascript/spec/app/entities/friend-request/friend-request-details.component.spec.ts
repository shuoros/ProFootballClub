/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FriendRequestDetailComponent from '@/entities/friend-request/friend-request-details.vue';
import FriendRequestClass from '@/entities/friend-request/friend-request-details.component';
import FriendRequestService from '@/entities/friend-request/friend-request.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FriendRequest Management Detail Component', () => {
    let wrapper: Wrapper<FriendRequestClass>;
    let comp: FriendRequestClass;
    let friendRequestServiceStub: SinonStubbedInstance<FriendRequestService>;

    beforeEach(() => {
      friendRequestServiceStub = sinon.createStubInstance<FriendRequestService>(FriendRequestService);

      wrapper = shallowMount<FriendRequestClass>(FriendRequestDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { friendRequestService: () => friendRequestServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFriendRequest = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        friendRequestServiceStub.find.resolves(foundFriendRequest);

        // WHEN
        comp.retrieveFriendRequest('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.friendRequest).toBe(foundFriendRequest);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFriendRequest = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        friendRequestServiceStub.find.resolves(foundFriendRequest);

        // WHEN
        comp.beforeRouteEnter({ params: { friendRequestId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.friendRequest).toBe(foundFriendRequest);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
