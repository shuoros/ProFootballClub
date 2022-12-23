/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import NewsPaperDetailComponent from '@/entities/news-paper/news-paper-details.vue';
import NewsPaperClass from '@/entities/news-paper/news-paper-details.component';
import NewsPaperService from '@/entities/news-paper/news-paper.service';
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
  describe('NewsPaper Management Detail Component', () => {
    let wrapper: Wrapper<NewsPaperClass>;
    let comp: NewsPaperClass;
    let newsPaperServiceStub: SinonStubbedInstance<NewsPaperService>;

    beforeEach(() => {
      newsPaperServiceStub = sinon.createStubInstance<NewsPaperService>(NewsPaperService);

      wrapper = shallowMount<NewsPaperClass>(NewsPaperDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { newsPaperService: () => newsPaperServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNewsPaper = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        newsPaperServiceStub.find.resolves(foundNewsPaper);

        // WHEN
        comp.retrieveNewsPaper('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.newsPaper).toBe(foundNewsPaper);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNewsPaper = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        newsPaperServiceStub.find.resolves(foundNewsPaper);

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
