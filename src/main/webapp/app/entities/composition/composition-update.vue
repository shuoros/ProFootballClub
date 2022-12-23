<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.composition.home.createOrEditLabel"
          data-cy="CompositionCreateUpdateHeading"
          v-text="$t('proFootballClubApp.composition.home.createOrEditLabel')"
        >
          Create or edit a Composition
        </h2>
        <div>
          <div class="form-group" v-if="composition.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="composition.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.d3fault')" for="composition-d3fault"
              >D 3 Fault</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="d3fault"
              id="composition-d3fault"
              data-cy="d3fault"
              :class="{ valid: !$v.composition.d3fault.$invalid, invalid: $v.composition.d3fault.$invalid }"
              v-model="$v.composition.d3fault.$model"
              required
            />
            <div v-if="$v.composition.d3fault.$anyDirty && $v.composition.d3fault.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.d3fault.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.arrange')" for="composition-arrange"
              >Arrange</label
            >
            <select
              class="form-control"
              name="arrange"
              :class="{ valid: !$v.composition.arrange.$invalid, invalid: $v.composition.arrange.$invalid }"
              v-model="$v.composition.arrange.$model"
              id="composition-arrange"
              data-cy="arrange"
              required
            >
              <option
                v-for="arrange in arrangeValues"
                :key="arrange"
                v-bind:value="arrange"
                v-bind:label="$t('proFootballClubApp.Arrange.' + arrange)"
              >
                {{ arrange }}
              </option>
            </select>
            <div v-if="$v.composition.arrange.$anyDirty && $v.composition.arrange.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.arrange.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.strategy')" for="composition-strategy"
              >Strategy</label
            >
            <select
              class="form-control"
              name="strategy"
              :class="{ valid: !$v.composition.strategy.$invalid, invalid: $v.composition.strategy.$invalid }"
              v-model="$v.composition.strategy.$model"
              id="composition-strategy"
              data-cy="strategy"
              required
            >
              <option
                v-for="strategy in strategyValues"
                :key="strategy"
                v-bind:value="strategy"
                v-bind:label="$t('proFootballClubApp.Strategy.' + strategy)"
              >
                {{ strategy }}
              </option>
            </select>
            <div v-if="$v.composition.strategy.$anyDirty && $v.composition.strategy.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.strategy.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.defence')" for="composition-defence"
              >Defence</label
            >
            <input
              type="number"
              class="form-control"
              name="defence"
              id="composition-defence"
              data-cy="defence"
              :class="{ valid: !$v.composition.defence.$invalid, invalid: $v.composition.defence.$invalid }"
              v-model.number="$v.composition.defence.$model"
              required
            />
            <div v-if="$v.composition.defence.$anyDirty && $v.composition.defence.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.defence.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.defence.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.defence.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.defence.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.shortPass')" for="composition-shortPass"
              >Short Pass</label
            >
            <input
              type="number"
              class="form-control"
              name="shortPass"
              id="composition-shortPass"
              data-cy="shortPass"
              :class="{ valid: !$v.composition.shortPass.$invalid, invalid: $v.composition.shortPass.$invalid }"
              v-model.number="$v.composition.shortPass.$model"
              required
            />
            <div v-if="$v.composition.shortPass.$anyDirty && $v.composition.shortPass.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.shortPass.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.shortPass.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.shortPass.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.shortPass.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.violence')" for="composition-violence"
              >Violence</label
            >
            <input
              type="number"
              class="form-control"
              name="violence"
              id="composition-violence"
              data-cy="violence"
              :class="{ valid: !$v.composition.violence.$invalid, invalid: $v.composition.violence.$invalid }"
              v-model.number="$v.composition.violence.$model"
              required
            />
            <div v-if="$v.composition.violence.$anyDirty && $v.composition.violence.$invalid">
              <small class="form-text text-danger" v-if="!$v.composition.violence.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.violence.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.violence.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.composition.violence.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.capitan')" for="composition-capitan"
              >Capitan</label
            >
            <select class="form-control" id="composition-capitan" data-cy="capitan" name="capitan" v-model="composition.capitan">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="composition.capitan && playerOption.id === composition.capitan.id ? composition.capitan : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.composition.team')" for="composition-team">Team</label>
            <select class="form-control" id="composition-team" data-cy="team" name="team" v-model="composition.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="composition.team && teamOption.id === composition.team.id ? composition.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.composition.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./composition-update.component.ts"></script>
