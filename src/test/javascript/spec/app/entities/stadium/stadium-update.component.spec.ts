/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import StadiumUpdateComponent from '@/entities/stadium/stadium-update.vue';
import StadiumClass from '@/entities/stadium/stadium-update.component';
import StadiumService from '@/entities/stadium/stadium.service';

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
  describe('Stadium Management Update Component', () => {
    let wrapper: Wrapper<StadiumClass>;
    let comp: StadiumClass;
    let stadiumServiceStub: SinonStubbedInstance<StadiumService>;

    beforeEach(() => {
      stadiumServiceStub = sinon.createStubInstance<StadiumService>(StadiumService);

      wrapper = shallowMount<StadiumClass>(StadiumUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          stadiumService: () => stadiumServiceStub,
          alertService: () => new AlertService(),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.stadium = entity;
        stadiumServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stadiumServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.stadium = entity;
        stadiumServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stadiumServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStadium = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        stadiumServiceStub.find.resolves(foundStadium);
        stadiumServiceStub.retrieve.resolves([foundStadium]);

        // WHEN
        comp.beforeRouteEnter({ params: { stadiumId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.stadium).toBe(foundStadium);
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
