<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.cupBoard.home.createOrEditLabel"
          data-cy="CupBoardCreateUpdateHeading"
          v-text="$t('proFootballClubApp.cupBoard.home.createOrEditLabel')"
        >
          Create or edit a CupBoard
        </h2>
        <div>
          <div class="form-group" v-if="cupBoard.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cupBoard.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cupBoard.level')" for="cup-board-level">Level</label>
            <input
              type="number"
              class="form-control"
              name="level"
              id="cup-board-level"
              data-cy="level"
              :class="{ valid: !$v.cupBoard.level.$invalid, invalid: $v.cupBoard.level.$invalid }"
              v-model.number="$v.cupBoard.level.$model"
              required
            />
            <div v-if="$v.cupBoard.level.$anyDirty && $v.cupBoard.level.$invalid">
              <small class="form-text text-danger" v-if="!$v.cupBoard.level.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cupBoard.level.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.cupBoard.level.max" v-text="$t('entity.validation.max', { max: 12 })">
                This field cannot be longer than 12 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.cupBoard.level.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cupBoard.cup')" for="cup-board-cup">Cup</label>
            <select class="form-control" id="cup-board-cup" data-cy="cup" name="cup" v-model="cupBoard.cup">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cupBoard.cup && cupOption.id === cupBoard.cup.id ? cupBoard.cup : cupOption"
                v-for="cupOption in cups"
                :key="cupOption.id"
              >
                {{ cupOption.id }}
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
            :disabled="$v.cupBoard.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cup-board-update.component.ts"></script>
