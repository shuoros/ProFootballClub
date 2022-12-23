/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PrivateChatDetailComponent from '@/entities/private-chat/private-chat-details.vue';
import PrivateChatClass from '@/entities/private-chat/private-chat-details.component';
import PrivateChatService from '@/entities/private-chat/private-chat.service';
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
  describe('PrivateChat Management Detail Component', () => {
    let wrapper: Wrapper<PrivateChatClass>;
    let comp: PrivateChatClass;
    let privateChatServiceStub: SinonStubbedInstance<PrivateChatService>;

    beforeEach(() => {
      privateChatServiceStub = sinon.createStubInstance<PrivateChatService>(PrivateChatService);

      wrapper = shallowMount<PrivateChatClass>(PrivateChatDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { privateChatService: () => privateChatServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrivateChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        privateChatServiceStub.find.resolves(foundPrivateChat);

        // WHEN
        comp.retrievePrivateChat('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.privateChat).toBe(foundPrivateChat);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        privateChatServiceStub.find.resolves(foundPrivateChat);

        // WHEN
        comp.beforeRouteEnter({ params: { privateChatId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.privateChat).toBe(foundPrivateChat);
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
