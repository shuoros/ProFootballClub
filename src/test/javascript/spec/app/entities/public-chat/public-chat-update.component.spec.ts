/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PublicChatUpdateComponent from '@/entities/public-chat/public-chat-update.vue';
import PublicChatClass from '@/entities/public-chat/public-chat-update.component';
import PublicChatService from '@/entities/public-chat/public-chat.service';

import TeamService from '@/entities/team/team.service';

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
  describe('PublicChat Management Update Component', () => {
    let wrapper: Wrapper<PublicChatClass>;
    let comp: PublicChatClass;
    let publicChatServiceStub: SinonStubbedInstance<PublicChatService>;

    beforeEach(() => {
      publicChatServiceStub = sinon.createStubInstance<PublicChatService>(PublicChatService);

      wrapper = shallowMount<PublicChatClass>(PublicChatUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          publicChatService: () => publicChatServiceStub,
          alertService: () => new AlertService(),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
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
        comp.publicChat = entity;
        publicChatServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicChatServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publicChat = entity;
        publicChatServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicChatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicChat = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        publicChatServiceStub.find.resolves(foundPublicChat);
        publicChatServiceStub.retrieve.resolves([foundPublicChat]);

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
