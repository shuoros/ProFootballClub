/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CompositionDetailComponent from '@/entities/composition/composition-details.vue';
import CompositionClass from '@/entities/composition/composition-details.component';
import CompositionService from '@/entities/composition/composition.service';
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
  describe('Composition Management Detail Component', () => {
    let wrapper: Wrapper<CompositionClass>;
    let comp: CompositionClass;
    let compositionServiceStub: SinonStubbedInstance<CompositionService>;

    beforeEach(() => {
      compositionServiceStub = sinon.createStubInstance<CompositionService>(CompositionService);

      wrapper = shallowMount<CompositionClass>(CompositionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { compositionService: () => compositionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundComposition = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        compositionServiceStub.find.resolves(foundComposition);

        // WHEN
        comp.retrieveComposition('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.composition).toBe(foundComposition);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundComposition = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        compositionServiceStub.find.resolves(foundComposition);

        // WHEN
        comp.beforeRouteEnter({ params: { compositionId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.composition).toBe(foundComposition);
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
