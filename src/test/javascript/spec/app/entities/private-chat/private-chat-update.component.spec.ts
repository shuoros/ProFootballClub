/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PrivateChatUpdateComponent from '@/entities/private-chat/private-chat-update.vue';
import PrivateChatClass from '@/entities/private-chat/private-chat-update.component';
import PrivateChatService from '@/entities/private-chat/private-chat.service';

import LeagueService from '@/entities/league/league.service';

import CupService from '@/entities/cup/cup.service';

import MessageService from '@/entities/message/message.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PrivateChat Management Update Component', () => {
    let wrapper: Wrapper<PrivateChatClass>;
    let comp: PrivateChatClass;
    let privateChatServiceStub: SinonStubbedInstance<PrivateChatService>;

    beforeEach(() => {
      privateChatServiceStub = sinon.createStubInstance<PrivateChatService>(PrivateChatService);

      wrapper = shallowMount<PrivateChatClass>(PrivateChatUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          privateChatService: () => privateChatServiceStub,
          alertService: () => new AlertService(),

          leagueService: () =>
            sinon.createStubInstance<LeagueService>(LeagueService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cupService: () =>
            sinon.createStubInstance<CupService>(CupService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          messageService: () =>
            sinon.createStubInstance<MessageService>(MessageService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.privateChat = entity;
        privateChatServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateChatServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.privateChat = entity;
        privateChatServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(privateChatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        privateChatServiceStub.find.resolves(foundPrivateChat);
        privateChatServiceStub.retrieve.resolves([foundPrivateChat]);

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
