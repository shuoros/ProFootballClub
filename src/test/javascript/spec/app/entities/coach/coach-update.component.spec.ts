/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CoachUpdateComponent from '@/entities/coach/coach-update.vue';
import CoachClass from '@/entities/coach/coach-update.component';
import CoachService from '@/entities/coach/coach.service';

import UserService from '@/entities/user/user.service';

import TeamService from '@/entities/team/team.service';
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
  describe('Coach Management Update Component', () => {
    let wrapper: Wrapper<CoachClass>;
    let comp: CoachClass;
    let coachServiceStub: SinonStubbedInstance<CoachService>;

    beforeEach(() => {
      coachServiceStub = sinon.createStubInstance<CoachService>(CoachService);

      wrapper = shallowMount<CoachClass>(CoachUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          coachService: () => coachServiceStub,
          alertService: () => new AlertService(),

          userService: () => new UserService(),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
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
        comp.coach = entity;
        coachServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(coachServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.coach = entity;
        coachServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(coachServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCoach = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        coachServiceStub.find.resolves(foundCoach);
        coachServiceStub.retrieve.resolves([foundCoach]);

        // WHEN
        comp.beforeRouteEnter({ params: { coachId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.coach).toBe(foundCoach);
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
