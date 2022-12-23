<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.coach.home.createOrEditLabel"
          data-cy="CoachCreateUpdateHeading"
          v-text="$t('proFootballClubApp.coach.home.createOrEditLabel')"
        >
          Create or edit a Coach
        </h2>
        <div>
          <div class="form-group" v-if="coach.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="coach.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.name')" for="coach-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="coach-name"
              data-cy="name"
              :class="{ valid: !$v.coach.name.$invalid, invalid: $v.coach.name.$invalid }"
              v-model="$v.coach.name.$model"
              required
            />
            <div v-if="$v.coach.name.$anyDirty && $v.coach.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.coach.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.banned')" for="coach-banned">Banned</label>
            <input
              type="checkbox"
              class="form-check"
              name="banned"
              id="coach-banned"
              data-cy="banned"
              :class="{ valid: !$v.coach.banned.$invalid, invalid: $v.coach.banned.$invalid }"
              v-model="$v.coach.banned.$model"
              required
            />
            <div v-if="$v.coach.banned.$anyDirty && $v.coach.banned.$invalid">
              <small class="form-text text-danger" v-if="!$v.coach.banned.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.abandoned')" for="coach-abandoned">Abandoned</label>
            <input
              type="checkbox"
              class="form-check"
              name="abandoned"
              id="coach-abandoned"
              data-cy="abandoned"
              :class="{ valid: !$v.coach.abandoned.$invalid, invalid: $v.coach.abandoned.$invalid }"
              v-model="$v.coach.abandoned.$model"
              required
            />
            <div v-if="$v.coach.abandoned.$anyDirty && $v.coach.abandoned.$invalid">
              <small class="form-text text-danger" v-if="!$v.coach.abandoned.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.subscribed')" for="coach-subscribed">Subscribed</label>
            <input
              type="checkbox"
              class="form-check"
              name="subscribed"
              id="coach-subscribed"
              data-cy="subscribed"
              :class="{ valid: !$v.coach.subscribed.$invalid, invalid: $v.coach.subscribed.$invalid }"
              v-model="$v.coach.subscribed.$model"
              required
            />
            <div v-if="$v.coach.subscribed.$anyDirty && $v.coach.subscribed.$invalid">
              <small class="form-text text-danger" v-if="!$v.coach.subscribed.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.plan')" for="coach-plan">Plan</label>
            <select
              class="form-control"
              name="plan"
              :class="{ valid: !$v.coach.plan.$invalid, invalid: $v.coach.plan.$invalid }"
              v-model="$v.coach.plan.$model"
              id="coach-plan"
              data-cy="plan"
              required
            >
              <option v-for="plan in planValues" :key="plan" v-bind:value="plan" v-bind:label="$t('proFootballClubApp.Plan.' + plan)">
                {{ plan }}
              </option>
            </select>
            <div v-if="$v.coach.plan.$anyDirty && $v.coach.plan.$invalid">
              <small class="form-text text-danger" v-if="!$v.coach.plan.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.coach.user')" for="coach-user">User</label>
            <select class="form-control" id="coach-user" data-cy="user" name="user" v-model="coach.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="coach.user && userOption.id === coach.user.id ? coach.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
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
            :disabled="$v.coach.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./coach-update.component.ts"></script>
