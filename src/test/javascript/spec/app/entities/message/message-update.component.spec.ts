/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MessageUpdateComponent from '@/entities/message/message-update.vue';
import MessageClass from '@/entities/message/message-update.component';
import MessageService from '@/entities/message/message.service';

import TeamService from '@/entities/team/team.service';

import TicketService from '@/entities/ticket/ticket.service';

import PrivateChatService from '@/entities/private-chat/private-chat.service';

import PublicChatService from '@/entities/public-chat/public-chat.service';
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
  describe('Message Management Update Component', () => {
    let wrapper: Wrapper<MessageClass>;
    let comp: MessageClass;
    let messageServiceStub: SinonStubbedInstance<MessageService>;

    beforeEach(() => {
      messageServiceStub = sinon.createStubInstance<MessageService>(MessageService);

      wrapper = shallowMount<MessageClass>(MessageUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          messageService: () => messageServiceStub,
          alertService: () => new AlertService(),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          ticketService: () =>
            sinon.createStubInstance<TicketService>(TicketService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          privateChatService: () =>
            sinon.createStubInstance<PrivateChatService>(PrivateChatService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          publicChatService: () =>
            sinon.createStubInstance<PublicChatService>(PublicChatService, {
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
        comp.message = entity;
        messageServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.message = entity;
        messageServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMessage = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        messageServiceStub.find.resolves(foundMessage);
        messageServiceStub.retrieve.resolves([foundMessage]);

        // WHEN
        comp.beforeRouteEnter({ params: { messageId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.message).toBe(foundMessage);
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
