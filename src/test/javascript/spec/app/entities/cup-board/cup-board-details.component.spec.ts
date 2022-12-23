/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CupBoardDetailComponent from '@/entities/cup-board/cup-board-details.vue';
import CupBoardClass from '@/entities/cup-board/cup-board-details.component';
import CupBoardService from '@/entities/cup-board/cup-board.service';
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
  describe('CupBoard Management Detail Component', () => {
    let wrapper: Wrapper<CupBoardClass>;
    let comp: CupBoardClass;
    let cupBoardServiceStub: SinonStubbedInstance<CupBoardService>;

    beforeEach(() => {
      cupBoardServiceStub = sinon.createStubInstance<CupBoardService>(CupBoardService);

      wrapper = shallowMount<CupBoardClass>(CupBoardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cupBoardService: () => cupBoardServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCupBoard = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupBoardServiceStub.find.resolves(foundCupBoard);

        // WHEN
        comp.retrieveCupBoard('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.cupBoard).toBe(foundCupBoard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCupBoard = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupBoardServiceStub.find.resolves(foundCupBoard);

        // WHEN
        comp.beforeRouteEnter({ params: { cupBoardId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cupBoard).toBe(foundCupBoard);
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
