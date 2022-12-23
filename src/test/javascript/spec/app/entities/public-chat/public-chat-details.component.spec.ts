/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicChatDetailComponent from '@/entities/public-chat/public-chat-details.vue';
import PublicChatClass from '@/entities/public-chat/public-chat-details.component';
import PublicChatService from '@/entities/public-chat/public-chat.service';
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
  describe('PublicChat Management Detail Component', () => {
    let wrapper: Wrapper<PublicChatClass>;
    let comp: PublicChatClass;
    let publicChatServiceStub: SinonStubbedInstance<PublicChatService>;

    beforeEach(() => {
      publicChatServiceStub = sinon.createStubInstance<PublicChatService>(PublicChatService);

      wrapper = shallowMount<PublicChatClass>(PublicChatDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publicChatService: () => publicChatServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublicChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        publicChatServiceStub.find.resolves(foundPublicChat);

        // WHEN
        comp.retrievePublicChat('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.publicChat).toBe(foundPublicChat);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        publicChatServiceStub.find.resolves(foundPublicChat);

        // WHEN
        comp.beforeRouteEnter({ params: { publicChatId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publicChat).toBe(foundPublicChat);
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
