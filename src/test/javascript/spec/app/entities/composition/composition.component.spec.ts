/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CompositionComponent from '@/entities/composition/composition.vue';
import CompositionClass from '@/entities/composition/composition.component';
import CompositionService from '@/entities/composition/composition.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Composition Management Component', () => {
    let wrapper: Wrapper<CompositionClass>;
    let comp: CompositionClass;
    let compositionServiceStub: SinonStubbedInstance<CompositionService>;

    beforeEach(() => {
      compositionServiceStub = sinon.createStubInstance<CompositionService>(CompositionService);
      compositionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CompositionClass>(CompositionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          compositionService: () => compositionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      compositionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllCompositions();
      await comp.$nextTick();

      // THEN
      expect(compositionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.compositions[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      compositionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(compositionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeComposition();
      await comp.$nextTick();

      // THEN
      expect(compositionServiceStub.delete.called).toBeTruthy();
      expect(compositionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
