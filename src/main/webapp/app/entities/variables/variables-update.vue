<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.variables.home.createOrEditLabel"
          data-cy="VariablesCreateUpdateHeading"
          v-text="$t('proFootballClubApp.variables.home.createOrEditLabel')"
        >
          Create or edit a Variables
        </h2>
        <div>
          <div class="form-group" v-if="variables.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="variables.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('proFootballClubApp.variables.leagueFirstPlacePrize')"
              for="variables-leagueFirstPlacePrize"
              >League First Place Prize</label
            >
            <input
              type="number"
              class="form-control"
              name="leagueFirstPlacePrize"
              id="variables-leagueFirstPlacePrize"
              data-cy="leagueFirstPlacePrize"
              :class="{ valid: !$v.variables.leagueFirstPlacePrize.$invalid, invalid: $v.variables.leagueFirstPlacePrize.$invalid }"
              v-model.number="$v.variables.leagueFirstPlacePrize.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('proFootballClubApp.variables.leagueSecondPlacePrize')"
              for="variables-leagueSecondPlacePrize"
              >League Second Place Prize</label
            >
            <input
              type="number"
              class="form-control"
              name="leagueSecondPlacePrize"
              id="variables-leagueSecondPlacePrize"
              data-cy="leagueSecondPlacePrize"
              :class="{ valid: !$v.variables.leagueSecondPlacePrize.$invalid, invalid: $v.variables.leagueSecondPlacePrize.$invalid }"
              v-model.number="$v.variables.leagueSecondPlacePrize.$model"
            />
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
            :disabled="$v.variables.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./variables-update.component.ts"></script>
