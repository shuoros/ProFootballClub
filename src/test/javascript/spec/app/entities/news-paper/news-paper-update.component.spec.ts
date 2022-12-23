/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NewsPaperUpdateComponent from '@/entities/news-paper/news-paper-update.vue';
import NewsPaperClass from '@/entities/news-paper/news-paper-update.component';
import NewsPaperService from '@/entities/news-paper/news-paper.service';

import LeagueService from '@/entities/league/league.service';

import CupService from '@/entities/cup/cup.service';
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
  describe('NewsPaper Management Update Component', () => {
    let wrapper: Wrapper<NewsPaperClass>;
    let comp: NewsPaperClass;
    let newsPaperServiceStub: SinonStubbedInstance<NewsPaperService>;

    beforeEach(() => {
      newsPaperServiceStub = sinon.createStubInstance<NewsPaperService>(NewsPaperService);

      wrapper = shallowMount<NewsPaperClass>(NewsPaperUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          newsPaperService: () => newsPaperServiceStub,
          alertService: () => new AlertService(),

          leagueService: () =>
            sinon.createStubInstance<LeagueService>(LeagueService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cupService: () =>
            sinon.createStubInstance<CupService>(CupService, {
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
        comp.newsPaper = entity;
        newsPaperServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(newsPaperServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.newsPaper = entity;
        newsPaperServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(newsPaperServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNewsPaper = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        newsPaperServiceStub.find.resolves(foundNewsPaper);
        newsPaperServiceStub.retrieve.resolves([foundNewsPaper]);

        // WHEN
        comp.beforeRouteEnter({ params: { newsPaperId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.newsPaper).toBe(foundNewsPaper);
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
